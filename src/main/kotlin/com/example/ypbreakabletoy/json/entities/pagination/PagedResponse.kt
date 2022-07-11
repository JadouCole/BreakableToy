package com.example.ypbreakabletoy.json.entities.pagination

public data class PagedResponse<T>(
    val content: Iterable<T>,
    val pageData: PageData) {

    constructor(content: Iterable<T>, pageNumber: Int, totalElements: Long, totalPages: Int) :
            this(content, PageData(pageNumber, totalElements, totalPages)
            )
}