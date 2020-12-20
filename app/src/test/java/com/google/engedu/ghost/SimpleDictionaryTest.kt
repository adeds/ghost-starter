package com.google.engedu.ghost

import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import java.io.FileInputStream

@RunWith(JUnit4::class)
class SimpleDictionaryTest {
    private lateinit var dictionary: SimpleDictionary

    @Before
    fun start() {
        MockitoAnnotations.initMocks(this)
        val inputStream = FileInputStream("../app/src/main/assets/words.txt")
        dictionary = SimpleDictionary(inputStream)
    }

    @Test
    fun `get Word from start word`() {
        val anagram = dictionary.getAnyWordStartingWith("boq")
        println(anagram)
    }

    @Test
    fun `test substring`(){
        val init = "s"
        val list = listOf("stop", "stok", "sop")
        val sub = list.filter { it.substring(0,1) == init }
        println(sub.toString())
    }
}