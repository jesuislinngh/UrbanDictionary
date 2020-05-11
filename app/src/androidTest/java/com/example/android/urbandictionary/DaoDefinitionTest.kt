package com.example.android.urbandictionary

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.urbandictionary.data.DaoDefinitionItem
import com.example.android.urbandictionary.data.DataBaseDefinitions
import com.example.android.urbandictionary.data.DefinitionEntity
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DaoDefinitionTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var daoDefinitionItem: DaoDefinitionItem
    private lateinit var db: DataBaseDefinitions

    private val definition = DefinitionEntity(word = "someword", definition = "this is a mock definition",
        permalink = "http://someurl.com",
        thumbs_up = "30",
        sound_urls = "http://somesounds.com/wavfile1, http://somesounds.com/wavfile2, http://somesounds.com/wavfile3",
        author = "some author",
        defid = 1,
        current_vote = "0",
        written_on = "10192929239293",
        example = "this is an example",
        thumbs_down = "300")

    private val definition1 = DefinitionEntity(word = "someword", definition = "this is one mock definition",
        permalink = "http://someurl.com",
        thumbs_up = "20",
        sound_urls = "http://somesounds.com/wavfile1, http://somesounds.com/wavfile2, http://somesounds.com/wavfile3",
        author = "some author",
        defid = 234,
        current_vote = "0",
        written_on = "10192929239293",
        example = "this is more than an example",
        thumbs_down = "50")

    private val definition2 = DefinitionEntity(word = "someword", definition = "this is another mock definition",
        permalink = "http://someurl.com",
        thumbs_up = "50",
        sound_urls = "http://somesounds.com/wavfile1, http://somesounds.com/wavfile2, http://somesounds.com/wavfile3",
        author = "some author",
        defid = 123,
        current_vote = "0",
        written_on = "10192929239293",
        example = "this is already an  example",
        thumbs_down = "30")

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, DataBaseDefinitions::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        daoDefinitionItem = db.daoDefinition()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetDefinition() {
        daoDefinitionItem.insert(definition)
        val definitions = getValue(daoDefinitionItem.getDefinitions("someword"))
        assertEquals("someword", definitions?.get(0)?.word )
    }

    @Test
    @Throws(Exception::class)
    fun insertAllDefinitions() {
        daoDefinitionItem.insertAll(listOf(definition, definition1, definition2))

        val definitions = getValue(daoDefinitionItem.getDefinitions("someword"))
        assertNotEquals(definitions, null)
        assertEquals(3, definitions?.size)
    }

    @Test
    @Throws(Exception::class)
    fun getAllDefinitionsOfTerm() {
        daoDefinitionItem.insertAll(listOf(definition, definition1))

        val definitions = getValue(daoDefinitionItem.getDefinitions("someword"))
        assertEquals("someword", definitions.get(0).word)
        assertEquals("someword", definitions.get(1).word)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllDefinitionsFromTerm() {
        daoDefinitionItem.insert(definition1)
        daoDefinitionItem.insert(definition2)
        daoDefinitionItem.deleteAll()
        val definitions = getValue(daoDefinitionItem.getDefinitions("someword"))
        assertTrue(definitions.isEmpty())
    }
}