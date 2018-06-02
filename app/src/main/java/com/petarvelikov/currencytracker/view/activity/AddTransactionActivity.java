package com.petarvelikov.currencytracker.view.activity;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.petarvelikov.currencytracker.R;
import com.petarvelikov.currencytracker.app.CurrencyTrackerApplication;
import com.petarvelikov.currencytracker.model.CryptoCurrency;
import com.petarvelikov.currencytracker.model.TransactionStatus;
import com.petarvelikov.currencytracker.preferences.SharedPreferencesHelper;
import com.petarvelikov.currencytracker.utils.CalendarUtils;
import com.petarvelikov.currencytracker.view.autocomplete.AutoCompleteCoinAdapter;
import com.petarvelikov.currencytracker.view.autocomplete.AutoCompleteCoinTextView;
import com.petarvelikov.currencytracker.view.validation.DoubleValueValidator;
import com.petarvelikov.currencytracker.view.validation.MandatoryFieldsValidator;
import com.petarvelikov.currencytracker.viewmodel.AddTransactionViewModel;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class AddTransactionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

  private static final String TAG = AddTransactionActivity.class.getSimpleName();

  @Inject
  ViewModelProvider.Factory viewModelFactory;
  @Inject
  SharedPreferencesHelper sharedPreferencesHelper;

  private AutoCompleteCoinTextView autoCompleteCoin;
  private EditText editTextCoinAmount;
  private EditText editTextCoinPrice;
  private TextView textViewDatePicker;
  private RadioGroup radioGroupTransactionType;
  private Button buttonAddTransaction;
  private View loadingScreen;

  private MandatoryFieldsValidator mandatoryFieldsValidator;
  private DoubleValueValidator doubleValueValidator;

  private AddTransactionViewModel viewModel;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_transaction);
    CurrencyTrackerApplication app = (CurrencyTrackerApplication) getApplication();
    app.getAppComponent().inject(this);
    viewModel = ViewModelProviders
        .of(this, viewModelFactory)
        .get(AddTransactionViewModel.class);
    viewModel.getCurrencies().observe(this, this::updateAutoCompleteCoinAdapter);
    viewModel.getTransactionStatus().observe(this, this::updateUiOnTransactionStatusChange);
    bindUi();
    setupValidators();
  }

  @Override
  public void onDateSet(DatePicker view, int year, int month, int day) {
    updateDateField(year, month, day);
  }

  private void updateDateField(int year, int month, int day) {
    String dateText = String.format(Locale.getDefault(), "%02d.%02d.%02d", day, month, year);
    textViewDatePicker.setText(dateText);
  }

  private void bindUi() {
    setupToolbar();
    setupAutoCompleteCoin();
    setupEditTextCoinAmount();
    setupEditTextCoinPrice();
    setupTextViewDatePicker();
    setupRadioGroupTransactionType();
    setupButtonAddTransaction();
    setupLoadingScreen();
  }

  private void setupToolbar() {
    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.add_transaction);
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_action_back);
    toolbar.setNavigationOnClickListener(view -> AddTransactionActivity.this.finish());
  }

  private void setupAutoCompleteCoin() {
    autoCompleteCoin = findViewById(R.id.autocompleteCoin);
    viewModel.loadAvailableCoins();
  }

  private void setupEditTextCoinAmount() {
    editTextCoinAmount = findViewById(R.id.editTextCoinAmount);
  }

  private void setupEditTextCoinPrice() {
    TextView textViewLabel = findViewById(R.id.textViewLabelCoinPrice);
    String labelText = String.format(Locale.getDefault(),
        getString(R.string.label_coin_price),
        sharedPreferencesHelper.getBaseCurrency());
    textViewLabel.setText(labelText);
    editTextCoinPrice = findViewById(R.id.editTextCoinPrice);
  }

  private void setupTextViewDatePicker() {
    textViewDatePicker = findViewById(R.id.textViewDatePicker);
    int year = CalendarUtils.currentYear();
    int month = CalendarUtils.currentMonth();
    int day = CalendarUtils.currentDayOfMonth();
    updateDateField(year, month, day);
    DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);
    textViewDatePicker.setOnClickListener(view -> datePickerDialog.show());
  }

  private void setupRadioGroupTransactionType() {
    radioGroupTransactionType = findViewById(R.id.radioGroupTransactionType);
  }

  private void setupButtonAddTransaction() {
    buttonAddTransaction = findViewById(R.id.buttonAddTransaction);
    buttonAddTransaction.setOnClickListener(view -> {
      String errorMessage = getString(R.string.error_empty_field);
      if (mandatoryFieldsValidator.validateAndShowErrors(errorMessage)) {
        errorMessage = getString(R.string.error_invalid_real_number);
        if (doubleValueValidator.validateAndShowErrors(errorMessage)) {
          parseInput();
        }
      }
    });
  }

  private void setupLoadingScreen() {
    loadingScreen = findViewById(R.id.loading_screen);
  }

  private void setupValidators() {
    mandatoryFieldsValidator = new MandatoryFieldsValidator();
    mandatoryFieldsValidator.addFields(autoCompleteCoin, editTextCoinAmount, editTextCoinPrice);
    doubleValueValidator = new DoubleValueValidator();
    doubleValueValidator.addFields(editTextCoinAmount, editTextCoinPrice);
  }

  private void parseInput() {
    String coinName = autoCompleteCoin.getText().toString().trim();
    double coinAmount = Double.parseDouble(editTextCoinAmount.getText().toString().trim());
    double coinPrice = Double.parseDouble(editTextCoinPrice.getText().toString().trim());
    String date = textViewDatePicker.getText().toString().trim();
    int checkedRadioButtonId = radioGroupTransactionType.getCheckedRadioButtonId();
    boolean isPurchase = checkedRadioButtonId == R.id.radioButtonPurchase;
    viewModel.onCompleteTransactionDataEntered(coinName, coinAmount, coinPrice, date, isPurchase);
  }

  private void updateUiOnTransactionStatusChange(TransactionStatus transactionStatus) {
    switch (transactionStatus) {
      case LOADING:
        loadingScreen.setVisibility(View.VISIBLE);
        break;
      case INITIAL:
        loadingScreen.setVisibility(View.GONE);
        break;
      case SUCCESS:
        loadingScreen.setVisibility(View.GONE);
        finish();
        break;
      case ERROR:
        showError();
        break;
    }
  }

  private void updateAutoCompleteCoinAdapter(List<CryptoCurrency> cryptoCurrencies) {
    AutoCompleteCoinAdapter adapter =
        new AutoCompleteCoinAdapter(this, R.layout.item_autocomplete_coin, cryptoCurrencies);
    autoCompleteCoin.setAdapter(adapter);
  }

  private void showError() {
    Toast.makeText(this, R.string.error_while_saving_transaction, Toast.LENGTH_LONG).show();
  }
}
