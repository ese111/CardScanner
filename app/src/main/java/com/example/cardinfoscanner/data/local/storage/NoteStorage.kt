package com.example.cardinfoscanner.data.local.storage

import com.example.cardinfoscanner.data.local.datastore.NoteDataStore
import com.example.cardinfoscanner.stateholder.note.Note
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteStorage @Inject constructor(
    private val noteDataStore: NoteDataStore
) {
   private val list = listOf(
        Note(
            0,
            "description 0",
            "A module can be installed in multiple components. For example, maybe you have a binding in ViewComponent and ViewWithFragmentComponent and do not want to duplicate modules. @InstallIn({ViewComponent.class, ViewWithFragmentComponent.class}) will install a module in both components.",
            "22.04.24"
        ),
        Note(
            1,
            "description 1",
            "A module can be installed in multiple components. For example, maybe you have a binding in ViewComponent and ViewWithFragmentComponent and do not want to duplicate modules. @InstallIn({ViewComponent.class, ViewWithFragmentComponent.class}) will install a module in both components.",
            "22.04.24"
        ),
        Note(
            2,
            "description 2",
            "A module can be installed in multiple components. For example, maybe you have a binding in ViewComponent and ViewWithFragmentComponent and do not want to duplicate modules. @InstallIn({ViewComponent.class, ViewWithFragmentComponent.class}) will install a module in both components.",
            "22.04.24"
        ),
        Note(
            3,
            "description 3",
            "A module can be installed in multiple components. For example, maybe you have a binding in ViewComponent and ViewWithFragmentComponent and do not want to duplicate modules. @InstallIn({ViewComponent.class, ViewWithFragmentComponent.class}) will install a module in both components.",
            "22.04.24"
        ),
        Note(
            4,
            "description 4",
            "A module can be installed in multiple components. For example, maybe you have a binding in ViewComponent and ViewWithFragmentComponent and do not want to duplicate modules. @InstallIn({ViewComponent.class, ViewWithFragmentComponent.class}) will install a module in both components.",
            "22.04.24"
        ),
        Note(
            5,
            "description 5",
            "A module can be installed in multiple components. For example, maybe you have a binding in ViewComponent and ViewWithFragmentComponent and do not want to duplicate modules. @InstallIn({ViewComponent.class, ViewWithFragmentComponent.class}) will install a module in both components.",
            "22.04.24"
        ),
       Note(
           6,
           "description 6",
           "A module can be installed in multiple components. For example, maybe you have a binding in ViewComponent and ViewWithFragmentComponent and do not want to duplicate modules. @InstallIn({ViewComponent.class, ViewWithFragmentComponent.class}) will install a module in both components.",
           "22.04.24"
       ),
       Note(
           7,
           "description 7",
           "A module can be installed in multiple components. For example, maybe you have a binding in ViewComponent and ViewWithFragmentComponent and do not want to duplicate modules. @InstallIn({ViewComponent.class, ViewWithFragmentComponent.class}) will install a module in both components.",
           "22.04.24"
       ),
       Note(
           8,
           "description 8",
           "A module can be installed in multiple components. For example, maybe you have a binding in ViewComponent and ViewWithFragmentComponent and do not want to duplicate modules. @InstallIn({ViewComponent.class, ViewWithFragmentComponent.class}) will install a module in both components.",
           "22.04.24"
       ),
       Note(
           9,
           "description 9",
           "A module can be installed in multiple components. For example, maybe you have a binding in ViewComponent and ViewWithFragmentComponent and do not want to duplicate modules. @InstallIn({ViewComponent.class, ViewWithFragmentComponent.class}) will install a module in both components.",
           "22.04.24"
       )
    )

    fun getNoteList() = noteDataStore.getNoteList()

    suspend fun setNoteList(json: String) = noteDataStore.setNoteList(json)
}