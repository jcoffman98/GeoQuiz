package com.bignerdranch.android.geoquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class QuizActivity : AppCompatActivity() {

    private lateinit var mTrueButton: Button
    private lateinit var mFalseButton: Button
    private lateinit var mNextButton: ImageButton
    private lateinit var mPrevButton: ImageButton
    private lateinit var mQuestionTextView: TextView
    private var mCurrentIndex = 0

    private val  mQuestionBank = arrayOf<Question>(Question(R.string.question_australia, true),
                                        Question(R.string.question_oceans, true),
                                        Question(R.string.question_mideast, false),
                                        Question(R.string.question_africa, false),
                                        Question(R.string.question_america, true),
                                        Question(R.string.question_asia, true))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        mQuestionTextView = findViewById(R.id.question_text_view)
        mQuestionTextView.setOnClickListener {
            Log.v("1", "text view clicked")
            mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.size
            updateQuestion()
        }
        updateQuestion()

        mTrueButton =  findViewById(R.id.true_button)
        mTrueButton.setOnClickListener {
            Log.v("1", "true button clicked")
            checkAnswer(true)
        }
        mFalseButton = findViewById(R.id.false_button)
        mFalseButton.setOnClickListener {
            Log.v("1", "false button clicked")
            checkAnswer(false)
        }

        mNextButton = findViewById(R.id.image_button_right)
        mNextButton.setOnClickListener {
            mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.size
            updateQuestion()
            Log.v("1", "next button clicked $mCurrentIndex, ${mQuestionBank.size}")
        }

        mPrevButton = findViewById(R.id.image_button_left)
        mPrevButton.setOnClickListener {
            if(mCurrentIndex > 0)
                mCurrentIndex = (mCurrentIndex-1)
            else
                mCurrentIndex = mQuestionBank.size - 1
            updateQuestion()
            Log.v("1", "prev button clicked $mCurrentIndex, ${mQuestionBank.size}")
        }

    }

    fun checkAnswer(userPressedTrue: Boolean) {
        val toast: Toast
        if(mQuestionBank[mCurrentIndex].mAnswerTrue == userPressedTrue)
            toast = Toast.makeText(this@QuizActivity, R.string.correct_toast, Toast.LENGTH_SHORT)
        else
            toast = Toast.makeText(this@QuizActivity, R.string.incorrect_toast, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP,0,0)
        toast.show()
    }
    fun updateQuestion() {
        val question = mQuestionBank[mCurrentIndex].mTextResId
        mQuestionTextView.setText(question)
    }

}

