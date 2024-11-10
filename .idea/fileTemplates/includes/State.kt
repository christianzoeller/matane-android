#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

#end

#parse("File Header.java")
sealed interface ${STATE} {
    data object Loading : ${STATE}

    data class Content(
        val data: Unit
    ) : ${STATE}

    data object Error : ${STATE}
}