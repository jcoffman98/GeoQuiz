package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class QuizActivity : AppCompatActivity() {
    private val TAG: String = "QuizActivity"
    private lateinit var mTrueButton: Button
    private lateinit var mFalseButton: Button
    private lateinit var mNextButton: ImageButton
    private lateinit var mPrevButton: ImageButton
    private lateinit var mQuestionTextView: TextView
    private lateinit var mCheatButton: Button
    private val REQUEST_CODE_CHEAT: Int = 0
    private var mCurrentIndex = 0
    private var answered = false
    private val KEY_INDEX: String = "index"
    private val KEY_CHEATER: String = "cheater"
    private var mCorrect = 0
    private var mNumAnswered = 0
    private var mIsCheater: Boolean = false

    private val  mQuestionBank = arrayOf<Question>(Question(R.string.question_australia, true),
                                        Question(R.string.question_oceans, true),
                                        Question(R.string.question_mideast, false),
                                        Question(R.string.question_africa, false),
                                        Question(R.string.question_america, true),
                                        Question(R.string.question_asia, true))

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(TAG, "onActivityResult")
        if(resultCode != Activity.RESULT_OK) {
            return
        }
        if(requestCode == REQUEST_CODE_CHEAT) {
            if(data != null) {
                mIsCheater = CheatActivity.wasAnswerShown(data)
            }
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstaneState")
        savedInstanceState?.putInt(KEY_INDEX, mCurrentIndex)
        savedInstanceState?.putBoolean(KEY_CHEATER, mIsCheater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        mCurrentIndex = savedInstanceState?.getInt(KEY_INDEX) ?: 0

        mQuestionTextView = findViewById(R.id.question_text_view)
        mQuestionTextView.setOnClickListener {
            Log.d(TAG, "text view clicked")
            mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.size
            updateQuestion()
        }
        updateQuestion()

        mTrueButton =  findViewById(R.id.true_button)
        mTrueButton.setOnClickListener {
            Log.d(TAG, "true button clicked")
            if(!answered) {
                checkAnswer(true)
                enableAnswerButtons(false)
            }
        }
        mFalseButton = findViewById(R.id.false_button)
        mFalseButton.setOnClickListener {
            Log.d(TAG, "false button clicked")
            if(!answered) {
                checkAnswer(false)
                enableAnswerButtons(false)
            }
        }

        mNextButton = findViewById(R.id.image_button_right)
        mNextButton.setOnClickListener {
            mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.size
            mIsCheater = false
            updateQuestion()
            if(answered)
                enableAnswerButtons(true)
            Log.d(TAG, "next button clicked $mCurrentIndex, ${mQuestionBank.size}")
        }

        mCheatButton = findViewById(R.id.cheat_button)
        mCheatButton.setOnClickListener {
            Log.d(TAG, "Cheat button clicked!")
            val intent = CheatActivity.newIntent(this@QuizActivity, mQuestionBank[mCurrentIndex].mAnswerTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }

        mPrevButton = findViewById(R.id.image_button_left)
        mPrevButton.setOnClickListener {
            if(mCurrentIndex > 0)
                mCurrentIndex = (mCurrentIndex-1)
            else
                mCurrentIndex = mQuestionBank.size - 1
            updateQuestion()
            if(answered)
                    enableAnswerButtons(true)
            Log.d(TAG, "prev button clicked $mCurrentIndex, ${mQuestionBank.size}")
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    fun enableAnswerButtons(enable: Boolean) {
        answered = !enable
        mTrueButton.isEnabled = enable
        mFalseButton.isEnabled = enable
    }

    fun checkAnswer(userPressedTrue: Boolean) {
        val answerIsTrue = mQuestionBank[mCurrentIndex].mAnswerTrue
        val toast: Toast
        var messageResID = 0
        if(mIsCheater) {
            messageResID = R.string.judgment_toast
        } else {
            if(answerIsTrue == userPressedTrue) {
                messageResID = R.string.correct_toast
                //toast = Toast.makeText(this@QuizActivity, R.string.correct_toast, Toast.LENGTH_SHORT)
                mCorrect += 1
            }
            else {
                messageResID = R.string.incorrect_toast
                //toast = Toast.makeText(this@QuizActivity, R.string.incorrect_toast, Toast.LENGTH_SHORT)
            }
        }

        toast = Toast.makeText(this@QuizActivity, messageResID, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP,0,0)
        toast.show()

        mNumAnswered = (mNumAnswered + 1)
        if(mNumAnswered >= mQuestionBank.size-1) {
            val result = mCorrect
            Toast.makeText(this@QuizActivity, "$result out of ${mQuestionBank.size-1}", Toast.LENGTH_LONG).show()
            mCorrect = 0
            mNumAnswered = 0
        }
    }
    fun updateQuestion() {
        val question = mQuestionBank[mCurrentIndex].mTextResId
        mQuestionTextView.setText(question)
    }

}

