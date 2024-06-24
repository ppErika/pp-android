package com.pp.domain.usecase.comment

import com.pp.domain.model.comments.PostCommentRequest
import com.pp.domain.repository.PpApiRepository
import com.pp.domain.utils.RemoteError
import javax.inject.Inject

class PostCommentUseCase @Inject constructor(
    private val ppApiRepository: PpApiRepository
) {
    suspend fun execute(
        remoteError: RemoteError,
        postId: Int,
        postCommentRequest: PostCommentRequest
    ): String? =
        ppApiRepository.postComment(remoteError, postId, postCommentRequest)
}