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

class TrieNode {
    val children: HashMap<String, TrieNode> = HashMap()
    var isWord: Boolean = false


    fun add(s: String?) {
        if (s.isNullOrEmpty()) {
            isWord = true
            return
        }

        if (children.containsKey(s.take(1)).not()) {
            children[s.take(1)] = TrieNode()
        }
        children[s.take(1)]?.add(s.drop(1))
    }

    fun isWord(s: String?): Boolean {
        return if (s.isNullOrEmpty()) {
            return isWord
        } else {
            if (children.containsKey(s.take(1))) {
                children[s.take(1)]!!.isWord(s.drop(1))
            } else false
        }
    }

    fun getAnyWordStartingWith(s: String?): String {
        return if (s.isNullOrEmpty()) {
            var candidate= ""
            for (it in children) {
                if (it.value.isWord.not()) {
                    candidate = it.key
                    break
                }
            }
            return candidate
        } else {
            if (children.containsKey(s.take(1))) {
                children[s.take(1)]!!.getAnyWordStartingWith(s.drop(1))
            } else ""
        }
    }

    fun getGoodWordStartingWith(s: String?): String? {
        return null
    }
}