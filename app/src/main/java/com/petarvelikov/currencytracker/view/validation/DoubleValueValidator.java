package com.petarvelikov.currencytracker.view.validation;

import android.text.TextUtils;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class handling validation of EditTexts that expect real numbers entered.
 */
public class DoubleValueValidator {

  private List<EditText> doubleFields;

  public DoubleValueValidator() {
    doubleFields = new ArrayList<>();
  }

  public DoubleValueValidator(EditText... fields) {
    this();
    doubleFields.addAll(Arrays.asList(fields));
  }

  public void addField(EditText field) {
    doubleFields.add(field);
  }

  public void addFields(EditText... fields) {
    doubleFields.addAll(Arrays.asList(fields));
  }

  public boolean removeField(EditText field) {
    return doubleFields.remove(field);
  }

  public boolean validate() {
    for (EditText field : doubleFields) {
      if (!validateField(field)) {
        return false;
      }
    }
    return true;
  }

  public boolean validateAndShowErrors(String errorMessage) {
    boolean isValid = true;
    for (EditText field : doubleFields) {
      if (!validateField(field)) {
        isValid = false;
        field.setError(errorMessage);
      }
    }
    return isValid;
  }

  private boolean validateField(EditText field) {
    String text = field.getText().toString();
    if (TextUtils.isEmpty(text)) {
      return false;
    }
    try {
      double value = Double.parseDouble(text);
      return true;
    } catch (NumberFormatException ex) {
      return false;
    }
  }
}
