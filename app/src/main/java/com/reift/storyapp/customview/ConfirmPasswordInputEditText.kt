package com.reift.storyapp.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.reift.storyapp.`interface`.AuthTextListener

class ConfirmPasswordInputEditText: TextInputEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun validateConfirmPassword(textInputLayout: TextInputLayout, password: PasswordInputEditText) {
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(text.toString() != password.text.toString()){
                    textInputLayout.helperText = "Confirm password must be match with Password above"
                } else {
                    textInputLayout.helperText = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

}