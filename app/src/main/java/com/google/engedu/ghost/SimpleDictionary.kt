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

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.random.Random

class SimpleDictionary(wordListStream: InputStream?) : GhostDictionary {
    val words: ArrayList<String>

    override fun isWord(word: String): Boolean {
        return words.contains(word)
    }

    override fun getAnyWordStartingWith(prefix: String): String? {
        return binarySearch(prefix)?.substring(0, prefix.length + 1)
    }

    override fun getGoodWordStartingWith(prefix: String): String {
        val selected: String? = null
        return selected!!
    }

    fun binarySearch(x: String): String? {
        var low = 0
        var high = words.size - 1
        while (low < high) {
            val m = low + (high - low) / 2
            val res = x.compareTo(words[m])

            //is found with param?
            if (words[m].startsWith(x)) return words[m]

            if (res > 0) low = m + 1
            else high = m - 1
        }
        return null
    }

    init {
        val `in` = BufferedReader(InputStreamReader(wordListStream))
        words = ArrayList()
        var line: String?
        while (`in`.readLine().also { line = it } != null) {
            val word = line?.trim { it <= ' ' }
            if (word != null) {
                if (word.length >= GhostDictionary.MIN_WORD_LENGTH)
                    line?.trim { it <= ' ' }?.let {
                        words.add(it)
                    }
            }
        }
    }
}