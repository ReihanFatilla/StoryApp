package com.reift.storyapp.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ConfirmPasswordInputEditText: TextInputEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun validateUsername(textInputLayout: TextInputLayout, password: UsernameEditText): Boolean {
        var isValid = false
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(text.toString() != password.text.toString() && !text.isNullOrEmpty()){
                    textInputLayout.helperText = "Confirm password must be match with Password above"
                    isValid = false
                } else if(text.toString().contains(" ")){
                    textInputLayout.helperText = "Username Cannot contain WhiteSpace"
                    isValid = false
                } else {
                    textInputLayout.helperText = null
                    isValid = true
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        return isValid
    }

}