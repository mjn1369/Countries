package apps.mjn.domain.executer

import io.reactivex.Scheduler

interface PostExecutionThread {
    val scheduler: Scheduler
}