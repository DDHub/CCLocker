package cc.ddhub.cclocker.smart;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cc.ddhub.cclocker.smart.action.ActionResult;
import cc.ddhub.cclocker.smart.action.IAction;
import cc.ddhub.cclocker.smart.action.IActionCallBack;
import cc.ddhub.cclocker.smart.action.NodeActionResult;

/**
 * Created by denzelw on 16/8/8.
 */
public class SmartTask {
    private IActionCallBack mCallBack;
    private ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private InternalHandler mHandler;
    private AccessibilityNodeInfo mNode;
    private Queue<IAction> mActionQueue;
    private OnTaskExecuteListener mExecuteListener;

    public interface OnTaskExecuteListener {
        void onTaskExecuteStart();
        void onTaskExecuteFinish();
    }

    public SmartTask() {
        this.mActionQueue = new LinkedList<>();
        mHandler = new InternalHandler(this, Looper.myLooper());
    }

    public SmartTask(IActionCallBack callBack, OnTaskExecuteListener taskExecuteListener) {
        this();
        setActionCallBack(callBack);
        setExecuteListener(taskExecuteListener);
    }

    public void setActionCallBack(IActionCallBack callBack) {
        this.mCallBack = callBack;
    }

    public void setExecuteListener(OnTaskExecuteListener executeListener) {
        this.mExecuteListener = executeListener;
    }

    public SmartTask addAction(IAction action) {
        mActionQueue.offer(action);
        return this;
    }

    public Queue<IAction> getActions() {
        return mActionQueue;
    }

    public int actionSize() {
        return mActionQueue.size();
    }

    public void execute() {
        if (mExecuteListener != null) {
            mExecuteListener.onTaskExecuteStart();
            if (mActionQueue.isEmpty()) {
                mExecuteListener.onTaskExecuteFinish();
            }
        }
        executeNext();
    }

    public IAction nextAction() {
        return mActionQueue.peek();
    }

    private boolean executeNext() {
        if (mActionQueue != null) {
            IAction action = mActionQueue.poll();
            if (action != null) {
                executeAction(action);
                return true;
            }
        }
        return false;
    }

    private void executeAction(IAction action) {
        if (action.setNode(mNode)) {
            mNode = null;
        }
        if (mCallBack != null) {
            if (mCallBack.onPreExecute(action)) {
                mExecutor.execute(new WorkRunnable(action));
            } else {
                mCallBack.onExecuteCancel(action);
            }
        } else {
            mExecutor.execute(new WorkRunnable(action));
        }
    }

    private class WorkRunnable implements Runnable {
        private IAction mAction;

        public WorkRunnable(IAction action) {
            this.mAction = action;
        }

        @Override
        public void run() {
            ActionResult result = mAction.execute();
            if (result instanceof NodeActionResult) {
                mNode = ((NodeActionResult) result).getNode();
            }
            mHandler.obtainMessage(0, new Result(mAction, result)).sendToTarget();
        }
    }

    private class Result {
        IAction mAction;
        ActionResult mActionResult;

        public Result(IAction action, ActionResult actionResult) {
            this.mAction = action;
            this.mActionResult = actionResult;
        }
    }

    private static class InternalHandler extends Handler {
        private SmartTask mTask;

        public InternalHandler(SmartTask task, Looper looper) {
            super(looper);
            mTask = task;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object o = msg.obj;
            if (mTask != null) {
                if (o instanceof Result && mTask.mCallBack != null) {
                    Result result = (Result) o;
                    mTask.mCallBack.onExecuteDone(result.mAction, result.mActionResult);
                }
                if (!mTask.executeNext() && mTask.mExecuteListener != null) {
                    mTask.mExecuteListener.onTaskExecuteFinish();
                }
            }
        }

    }
}
