package com.reift.storyapp.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class UsernameInputEditText: TextInputEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun validateUsername(textInputLayout: TextInputLayout): MutableLiveData<Boolean> {
        val isValid = MutableLiveData(false)
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(text.toString().length < 5 && !text.isNullOrEmpty()){
                    textInputLayout.helperText = "Password lenght must be at least 5"
                    isValid.value = false
                } else if(text.toString().contains(" ")){
                    textInputLayout.helperText = "Username Cannot contain WhiteSpace"
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