package com.example.gourmetsearcher.manager

import android.content.Context
import android.content.SharedPreferences
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.anyString
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/** PreferencesManagerクラスのユニットテスト */
@RunWith(MockitoJUnitRunner::class)
class PreferencesManagerTest {
    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockSharedPreferences: SharedPreferences

    @Mock
    private lateinit var mockEditor: SharedPreferences.Editor

    private lateinit var preferencesManager: PreferencesManager

    /** テスト前の初期化 */
    @Before
    fun setup() {
        `when`(mockContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockSharedPreferences)
        `when`(mockSharedPreferences.edit()).thenReturn(mockEditor)
        `when`(mockEditor.putString(anyString(), anyString())).thenReturn(mockEditor)
        `when`(mockEditor.remove(anyString())).thenReturn(mockEditor)

        preferencesManager = PreferencesManager(mockContext)
    }

    /** 空の履歴に新しいアイテムを追加するテスト */
    @Test
    fun testAddNewToEmpty() {
        `when`(mockSharedPreferences.getString(anyString(), anyString())).thenReturn("")

        preferencesManager.saveHistoryItem("test")

        verify(mockEditor).putString("historyList", "test")
        verify(mockEditor).apply()
    }

    /** 既存の履歴に新しいアイテムを追加するテスト */
    @Test
    fun testAddNewToExisting() {
        `when`(mockSharedPreferences.getString(anyString(), anyString())).thenReturn("item1,item2")

        preferencesManager.saveHistoryItem("item3")

        verify(mockEditor).putString("historyList", "item1,item2,item3")
        verify(mockEditor).apply()
    }

    /** 履歴が最大数に達した時に最も古いアイテムを削除するテスト */
    @Test
    fun testRemoveOldest() {
        `when`(mockSharedPreferences.getString(anyString(), anyString())).thenReturn("item1,item2,item3,item4,item5")

        preferencesManager.saveHistoryItem("item6")

        verify(mockEditor).putString("historyList", "item2,item3,item4,item5,item6")
        verify(mockEditor).apply()
    }

    /** 重複を許さないことを確認するテスト */
    @Test
    fun testPreventDuplicates() {
        val expectedItems = listOf("item1", "item2", "item3")
        `when`(mockSharedPreferences.getString(anyString(), anyString())).thenReturn(expectedItems.joinToString(","))

        preferencesManager.saveHistoryItem("item2")

        assertEquals(expectedItems, preferencesManager.getHistoryList())

        verify(mockEditor).putString(anyString(), eq(expectedItems.joinToString(",")))
        verify(mockEditor).apply()
    }

    /** 履歴リストを取得するテスト */
    @Test
    fun testGetHistory() {
        `when`(mockSharedPreferences.getString(anyString(), anyString())).thenReturn("item1,item2,item3")

        val result = preferencesManager.getHistoryList()

        assertEquals(listOf("item1", "item2", "item3"), result)
    }

    /** 空の履歴リストを取得するテスト */
    @Test
    fun testGetEmptyHistory() {
        `when`(mockSharedPreferences.getString(anyString(), anyString())).thenReturn("")

        val result = preferencesManager.getHistoryList()

        assertEquals(emptyList<String>(), result)
    }

    /** 履歴をクリアするテスト */
    @Test
    fun testClearHistory() {
        preferencesManager.clearHistory()

        verify(mockEditor).remove("historyList")
        verify(mockEditor).apply()
    }
}
