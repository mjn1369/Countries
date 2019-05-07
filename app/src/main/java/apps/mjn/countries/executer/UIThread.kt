package apps.mjn.countries.executer

import apps.mjn.domain.executer.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class UIThread : PostExecutionThread {
    override val scheduler: Scheduler by lazy { AndroidSchedulers.mainThread() }
}