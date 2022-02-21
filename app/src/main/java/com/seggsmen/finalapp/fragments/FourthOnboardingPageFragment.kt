package com.seggsmen.finalapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.seggsmen.finalapp.OnboardingListener
import com.seggsmen.finalapp.R

class FourthOnboardingPageFragment : Fragment() {
    private lateinit var title: TextView
    private lateinit var nameEditText : EditText
    private lateinit var confirmButton: Button

    private lateinit var nameConfirmCaller: OnboardingListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var fragmentView = inflater.inflate(R.layout.onboarding_page_4, container, false)

        title = fragmentView.findViewById<TextView>(R.id.petNameTitle)

        nameEditText = fragmentView.findViewById<EditText>(R.id.petNameField)

        nameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                onClick()
            }
        }

        nameEditText.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                onTextConfirm()
            }

            false
        }

        confirmButton = fragmentView.findViewById<Button>(R.id.confirmButton)
        confirmButton.setOnClickListener { nameConfirmCaller?.onNameConfirmed(nameEditText.text.toString()) }

        return fragmentView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnboardingListener) {
            nameConfirmCaller = context
        }
    }

    private fun onClick() {
        confirmButton.visibility = View.INVISIBLE

        title.text = "Give them a name!"
    }

    private fun onTextConfirm() {
        if (nameEditText.text.toString() != "") {
            confirmButton.visibility = View.VISIBLE

            title.text = "This will be your pet's name!"

            nameEditText.clearFocus()
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        if (activity?.currentFocus == null) {
            return
        }

        val imm: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    companion object {
        fun newInstance() : FourthOnboardingPageFragment {
            return FourthOnboardingPageFragment()
        }
    }
}