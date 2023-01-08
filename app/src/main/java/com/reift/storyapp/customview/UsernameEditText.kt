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

class UsernameEditText: TextInputEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun validateLenght(textInputLayout: TextInputLayout): Boolean {
        var isValid = false
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(text.toString().length <= 8 && !text.isNullOrEmpty()){
                    textInputLayout.helperText = "Password lenght must be at least 8"
                    isValid = false
                } else if(text.toString().contains(" ")){
                    textInputLayout.helperText = "Password Cannot contain WhiteSpace"
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