package com.bignerdranch.android.geoquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class QuizActivity : AppCompatActivity() {

    private lateinit var mTrueButton: Button
    private lateinit var mFalseButton: Button
    private lateinit var mNextButton: Button
    private lateinit var mQuestionTextView: TextView

    private val  mQuestionBank = Array<Question>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        mTrueButton =  findViewById(R.id.true_button)
        mTrueButton.setOnClickListener {
            Log.v("1", "true button clicked")
            val toast = Toast.makeText(this@QuizActivity, R.string.correct_toast, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP,0,0)
            toast.show()
        }
        mFalseButton = findViewById(R.id.false_button)
        mFalseButton.setOnClickListener {
            Log.v("1", "false button clicked")
            val toast = Toast.makeText(this@QuizActivity, R.string.incorrect_toast, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP,0,0)
            toast.show()
        }
    }
}
