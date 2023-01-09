package com.reift.storyapp.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.reift.storyapp.R
import com.reift.storyapp.`interface`.AuthTextListener

class PasswordEditText: TextInputEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun validateLenght(textInputLayout: TextInputLayout): MutableLiveData<Boolean> {
        val isValid = MutableLiveData(false)
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(text.toString().length < 8 && !text.isNullOrEmpty()){
                    textInputLayout.helperText = context.getString(R.string.password_length)
                    isValid.value = false
                } else if(text.toString().contains(" ")){
                    textInputLayout.helperText = context.getString(R.string.password_white_space)
                    isValid.value = false
                } else {
                    textInputLayout.helperText = null
                    isValid.value = true
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        return isValid
    }

}