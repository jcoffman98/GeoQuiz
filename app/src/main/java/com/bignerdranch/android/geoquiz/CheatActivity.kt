package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class CheatActivity : AppCompatActivity() {
    private var mAnswerIsTrue: Boolean = false
    private lateinit var mAnswerTextView: TextView
    private lateinit var mShowAnswerButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        mAnswerTextView = findViewById(R.id.answer_text_view)
        mShowAnswerButton = findViewById(R.id.show_answer_button)
        mShowAnswerButton.setOnClickListener() {
            if(mAnswerIsTrue) {
                mAnswerTextView.setText(R.string.true_button)
            } else {
                mAnswerTextView.setText(R.string.false_button)
            }
            setAnswerShownResult(true)
        }
        mAnswerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
    }

    fun setAnswerShownResult(isAnswerShown: Boolean) : Unit {
        val data = Intent()
        data.putExtra(EXTRA_ANSWER_IS_SHOWN, isAnswerShown)
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        private const val EXTRA_ANSWER_IS_TRUE: String = "com.bignerdranch.android.geoquiz.answer_is_true"
        private const val EXTRA_ANSWER_IS_SHOWN: String = "com.bignerdranch.android.geoquiz.answer_shown"
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            val intent = Intent(packageContext, CheatActivity::class.java)
            intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            return intent
        }

        fun wasAnswerShown(result: Intent) : Boolean {
            return result.getBooleanExtra(EXTRA_ANSWER_IS_SHOWN, false)
        }
    }
}
