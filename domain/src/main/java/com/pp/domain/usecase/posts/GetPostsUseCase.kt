package com.pp.domain.usecase.posts

import com.pp.domain.model.post.GetPostsRequest
import com.pp.domain.model.post.GetPostsResponse
import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val ppApiRepository: PpApiRepository
) {
    suspend fun execute(
        remoteError: RemoteError,
        accessToken: String,
        getPostsRequest: GetPostsRequest
    ): GetPostsResponse? =
        ppApiRepository.getPosts(remoteError, accessToken, getPostsRequest)
}