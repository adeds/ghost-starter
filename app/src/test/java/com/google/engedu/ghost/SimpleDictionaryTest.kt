package com.google.engedu.ghost

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import java.io.FileInputStream

@RunWith(JUnit4::class)
class SimpleDictionaryTest {
    private lateinit var dictionary: SimpleDictionary
    private lateinit var fastDictionary: FastDictionary

    @Before
    fun start() {
        MockitoAnnotations.initMocks(this)
        val inputStream = FileInputStream("../app/src/main/assets/words.txt")
        val inputStream2 = FileInputStream("../app/src/main/assets/words-dummy.txt")
        dictionary = SimpleDictionary(inputStream)
        fastDictionary = FastDictionary(inputStream2)
    }

    @Test
    fun `get Word from start word`() {
        val anagram = dictionary.getAnyWordStartingWith("stop")
        println(anagram)
    }

    @Test
    fun `get Word size`() {
        val size = dictionary.words.size
        println(size)
    }

    @Test
    fun `check fast root`() {
        val test = fastDictionary.root
        println(test)
    }

    @Test
    fun `check get fast root`() {
        val test = fastDictionary.getAnyWordStartingWith("a")
        println(test)
    }

    @Test
    fun `test binary`() {
        val anagram = dictionary.binarySearch("stop")
        println(anagram)
    }

    @Test
    fun `test substring`() {
        val init = "s"
        val list = listOf("stop", "stok", "sop")
        val sub = list.filter { it.substring(0, 1) == init }
        println(sub.toString())
    }
}