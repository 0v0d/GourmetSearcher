package com.example.gourmetsearcher.manager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

/** PreferencesManagerクラスのユニットテスト */
@RunWith(MockitoJUnitRunner::class)
class PreferencesManagerTest {
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var dataStore: DataStore<Preferences>
    private val historyKey = stringPreferencesKey(HISTORY_KEY)

    private companion object {
        private const val HISTORY_KEY = "historyList"
        private const val MAX_HISTORY_SIZE = 5
    }

    /** テスト実行前に初期化する */
    @Before
    fun setup() {
        dataStore =
            PreferenceDataStoreFactory.create(
                produceFile = { File.createTempFile("test_datastore", ".preferences_pb") },
            )
        preferencesManager = PreferencesManager(dataStore)
    }

    /** テスト終了時にデータをクリアする */
    @After
    fun tearDown() =
        runBlocking {
            dataStore.edit { it.clear() }
        }

    /** 空の履歴に新しいアイテムを追加するテスト */
    @Test
    fun testAddNewToEmpty() =
        runBlocking {
            preferencesManager.saveHistoryItem("test")
            val historyList = preferencesManager.getHistoryList().first()
            assertEquals(listOf("test"), historyList)
        }

    /** 既存の履歴に新しいアイテムを追加するテスト */
    @Test
    fun testAddNewToExisting() =
        runBlocking {
            val initialItems = listOf("item1", "item2")
            dataStore.edit { it[historyKey] = initialItems.joinToString(",") }

            preferencesManager.saveHistoryItem("item3")
            val historyList = preferencesManager.getHistoryList().first()
            assertEquals(initialItems + "item3", historyList)
        }

    /** 履歴が最大数に達した時に最も古いアイテムを削除するテスト */
    @Test
    fun testRemoveOldest() =
        runBlocking {
            val initialItems = List(MAX_HISTORY_SIZE) { "item$it" }
            dataStore.edit { it[historyKey] = initialItems.joinToString(",") }

            preferencesManager.saveHistoryItem("newItem")
            val historyList = preferencesManager.getHistoryList().first()
            assertEquals(initialItems.drop(1) + "newItem", historyList)
            assertEquals(MAX_HISTORY_SIZE, historyList.size)
        }

    /** 重複を許さないことを確認するテスト */
    @Test
    fun testPreventDuplicates() =
        runBlocking {
            val initialItems = listOf("item1", "item2", "item3")
            dataStore.edit { it[historyKey] = initialItems.joinToString(",") }

            preferencesManager.saveHistoryItem("item2")
            val historyList = preferencesManager.getHistoryList().first()
            assertEquals(initialItems, historyList)
        }

    /** 履歴リストを取得するテスト */
    @Test
    fun testGetHistory() =
        runBlocking {
            val expectedItems = listOf("item1", "item2", "item3")
            dataStore.edit { it[historyKey] = expectedItems.joinToString(",") }

            val historyList = preferencesManager.getHistoryList().first()
            assertEquals(expectedItems, historyList)
        }

    /** 空の履歴リストを取得するテスト */
    @Test
    fun testGetEmptyHistory() =
        runBlocking {
            val historyList = preferencesManager.getHistoryList().first()
            assertTrue(historyList.isEmpty())
        }

    /** 履歴をクリアするテスト */
    @Test
    fun testClearHistory() =
        runBlocking {
            val initialItems = listOf("item1", "item2", "item3")
            dataStore.edit { it[historyKey] = initialItems.joinToString(",") }

            preferencesManager.clearHistory()
            val historyList = preferencesManager.getHistoryList().first()
            assertTrue(historyList.isEmpty())
        }

    /** 最大数のアイテムを保存するテスト */
    @Test
    fun testSaveMaxItems() =
        runBlocking {
            repeat(MAX_HISTORY_SIZE + 2) { preferencesManager.saveHistoryItem("item$it") }
            val historyList = preferencesManager.getHistoryList().first()
            assertEquals(MAX_HISTORY_SIZE, historyList.size)
            assertEquals("item${MAX_HISTORY_SIZE + 1}", historyList.last())
        }
}
