package cc.ddhub.cclocker.smart;

import java.util.ArrayList;
import java.util.List;

import cc.ddhub.cclocker.smart.action.IAction;
import cc.ddhub.cclocker.smart.action.IActionCallBack;

/**
 * Created by denzelw on 16/8/8.
 */
public class SmartTask {
    private IActionCallBack mCallBack;
    private List<IAction> mActionList;

    public SmartTask() {
        this.mActionList = new ArrayList<>();
    }

    public SmartTask(IActionCallBack callBack) {
        this();
        setCallBack(callBack);
    }

    public void setCallBack(IActionCallBack callBack) {
        this.mCallBack = callBack;
    }

    public SmartTask addAction(IAction action) {
        mActionList.add(action);
        return this;
    }

    public List<IAction> getActionList() {
        return mActionList;
    }

    public int actionSize() {
        return mActionList.size();
    }

    public IActionCallBack getCallBack() {
        return mCallBack;
    }
}
