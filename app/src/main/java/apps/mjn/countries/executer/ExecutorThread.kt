package apps.mjn.countries.executer

import apps.mjn.domain.executer.UseCaseExecutor
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class ExecutorThread : UseCaseExecutor {
    override val scheduler: Scheduler by lazy { Schedulers.io() }
}