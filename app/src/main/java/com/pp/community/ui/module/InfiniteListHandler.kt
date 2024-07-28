package com.pp.community.ui.module

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.filter

/**
 * 무한 스크롤 핸들러
 */
@Composable
fun InfiniteListHandler(
    listState: LazyListState? = null,
    gridListState: LazyGridState? = null,
    buffer: Int = 2,
    onLoadMore: () -> Unit
) {
    val loadMore = remember {
        derivedStateOf {
            listState?.let{
                val layoutInfo = it.layoutInfo
                val totalItemsNumber = layoutInfo.totalItemsCount
                val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

                lastVisibleItemIndex > (totalItemsNumber - buffer)
            }
            gridListState?.let{
                val layoutInfo = it.layoutInfo
                val totalItemsNumber = layoutInfo.totalItemsCount
                val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

                lastVisibleItemIndex > (totalItemsNumber - buffer)
            }

        }
    }

    LaunchedEffect(loadMore) {
        snapshotFlow { loadMore.value }
            .filter { it?:false }
            .collect {
                onLoadMore()
            }
    }
}