package com.example.rollcall.services

import com.example.rollcall.controllers.LessonController
import com.example.rollcall.models.Event
import com.example.rollcall.models.Lesson
import com.example.rollcall.models.User

class LessonService {
    companion object {
        @JvmStatic val onCurrentLessonChanged = Event<Lesson>()
    }
    val lessonController:LessonController = LessonController()

    suspend fun GetCurrentLesson(user: User):Boolean {
        var lesson:Lesson = Lesson()

        lesson = lessonController.GetCurrentLesson(user)

        if(lesson.Id != 0) {
            onCurrentLessonChanged.invoke(lesson)
            return true
        }

        return false
    }

    suspend fun CheckIntoLesson(user: User, lesson: Lesson, code: Int):String {
        if(code != lesson.Code) {
            return "Incorrect"
        }

        val result: Boolean = lessonController.CheckIntoLesson(user, lesson, code)

        if(result == true) {
            return "Success"
        }
        else {
            return "Fail"
        }
    }

    suspend fun CheckIfCheckedIn(user: User, lesson: Lesson): Boolean {
        val result: Boolean = lessonController.CheckIfCheckedIn(user, lesson)
        return result;
    }

    suspend fun GetLessonsByMonth(user: User, month: Int):List<Lesson> {
        val lessons:List<Lesson> = lessonController.GetLessonsByMonth(user, month)
        return lessons
    }
}