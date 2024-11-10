#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

#end
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

#parse("File Header.java")
sealed interface ${OVERVIEWSTATE} {
    sealed class Content(
        open val items: List<Unit>
    ) : ${OVERVIEWSTATE}

    data object Loading : Content(
        items = List(15) { skeletonListItem(it) } 
    )
    
    data class Data(
        override val items: List<Unit>
    ) : Content(
        items = items
    )
    
    data class LoadingMore(
        val currentItems: List<Unit>
    ) : Content(
        items = currentItems + List(10) { skeletonListItem(it) } 
    )
    
    data object Error : ${OVERVIEWSTATE}
}

sealed interface ${DETAILSTATE} {
    data object NoSelection : ${DETAILSTATE}
    
    sealed class Content(
        open val item: Unit
    ) : ${DETAILSTATE}
    
    data object Loading : Content(
        item = skeletonDetail
    )
    
    data class Data(
        override val item: Unit
    ) : Content(
        item = item
    )
    
    data object Error : ${DETAILSTATE}
}

@Parcelize
data class Selected${NAME}ListItem(
    val value: Unit
) : Parcelable

private fun skeletonListItem(id: Int) = Unit

private val skeletonDetail = Unit