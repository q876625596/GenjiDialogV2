package com.ly.genjidialogv2;

import androidx.appcompat.app.AppCompatActivity;

import com.ly.genjidialog.GenjiDialog;
import com.ly.genjidialog.other.DialogOptions;

public class BBB extends AppCompatActivity {
    public void main(String[] args) {
        DialogOptions dialogOptions = new DialogOptions();
        dialogOptions.setLayoutId(R.layout.aaa);
        new GenjiDialog().setDialogOptions(dialogOptions)
                .showOnWindow(getSupportFragmentManager());
    }
}
