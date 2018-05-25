package com.petarvelikov.currencytracker.view.validation;

import android.text.TextUtils;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MandatoryFieldsValidator {

  private List<EditText> mandatoryFields;

  public MandatoryFieldsValidator() {
    mandatoryFields = new ArrayList<>();
  }

  public MandatoryFieldsValidator(EditText... fields) {
    this();
    mandatoryFields.addAll(Arrays.asList(fields));
  }

  public void addField(EditText field) {
    mandatoryFields.add(field);
  }

  public void addFields(EditText... fields) {
    mandatoryFields.addAll(Arrays.asList(fields));
  }

  public boolean removeField(EditText field) {
    return mandatoryFields.remove(field);
  }

  public boolean validate() {
    for (EditText field : mandatoryFields) {
      if (TextUtils.isEmpty(field.getText())) {
        return false;
      }
    }
    return true;
  }

  public boolean validateAndShowErrors(String errorMessage) {
    boolean isValid = true;
    for (EditText field : mandatoryFields) {
      if (TextUtils.isEmpty(field.getText())) {
        isValid = false;
        field.setError(errorMessage);
      }
    }
    return isValid;
  }
}
