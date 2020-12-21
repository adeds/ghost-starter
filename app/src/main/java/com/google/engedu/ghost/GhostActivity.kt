/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.engedu.ghost

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.engedu.ghost.databinding.ActivityGhostBinding
import java.io.IOException
import java.util.*

class GhostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGhostBinding
    private lateinit var dictionary: GhostDictionary
    private val imm by lazy { getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    private var userTurn = true
    private var word = ""
    private val random = Random()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGhostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val assetManager = assets
        try {
            dictionary = FastDictionary(assetManager.open("words.txt"))
        } catch (e: IOException) {
            Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
        }
        /**
         *
         * YOUR CODE GOES HERE
         *
         */
        binding.clickListener()
    }

    private fun ActivityGhostBinding.clickListener() {
        btnRestart.setOnClickListener {
            word = ""
            binding.ghostText.text = ""
            startGame()
        }
        btnChallenge.setOnClickListener { }

        btnShowKeyboard.setOnClickListener { showKeyboard() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_ghost, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     *
     * @param view
     * @return true
     */
    private fun startGame() {
        if (userTurn) {
            binding.gameStatus.text = USER_TURN
        } else {
            binding.gameStatus.text = COMPUTER_TURN
            computerTurn()
        }
    }

    private fun showKeyboard() = imm.toggleSoftInputFromWindow(
        binding.ghost.applicationWindowToken,
        InputMethodManager.SHOW_FORCED,
        0
    )

    private fun computerTurn() {
        if (dictionary.isWord(word)) {
            binding.gameStatus.text = "computer win by challenge"
        } else {
            dictionary.getAnyWordStartingWith(word).let {
                if (it == null) {
                    binding.gameStatus.text = "computer win by challenge"
                } else {
                    word = it
                    binding.ghostText.text = word
                    // Do computer turn stuff then make it the user's turn again
                    userTurn = true
                    startGame()
                }
            }

        }
    }

    /**
     * Handler for user key presses.
     *
     * @param keyCode
     * @param event
     * @return whether the key stroke was handled.
     */
    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        Log.e("onKeyUp", keyCode.toString())
        if (keyCode in 28..54) {
            word += event.unicodeChar.toChar()
            binding.ghostText.text = word

            if (word.length >= GhostDictionary.MIN_WORD_LENGTH) {
                binding.gameStatus.text = "user win"
            } else {
                userTurn = false
                startGame()
            }
        }
        return super.onKeyUp(keyCode, event)
    }

    companion object {
        private const val COMPUTER_TURN = "Computer's turn"
        private const val USER_TURN = "Your turn"
    }
}