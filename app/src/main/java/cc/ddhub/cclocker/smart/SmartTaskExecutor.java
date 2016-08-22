package cc.ddhub.cclocker.smart;

import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

import cc.ddhub.cclocker.smart.action.ActionResult;
import cc.ddhub.cclocker.smart.action.IAction;
import cc.ddhub.cclocker.smart.action.IActionCallBack;
import cc.ddhub.cclocker.smart.action.INodeHandler;
import cc.ddhub.cclocker.smart.action.NodeActionResult;

/**
 * Created by denzelw on 16/8/8.
 */
public class SmartTaskExecutor {

    public void executeTask(SmartTask task) {
        if (task == null) {
            return;
        }
        IActionCallBack callBack = task.getCallBack();

        List<IAction> actionList = task.getActionList();
        AccessibilityNodeInfo node = null;
        for (IAction action : actionList) {
            if (node != null && action instanceof INodeHandler) {
                ((INodeHandler) action).setNode(node);
                node = null;
            }
            if (callBack != null) {
                if (callBack.onPreExecute(action)) {
                    ActionResult result = action.execute();
                    if (result instanceof NodeActionResult) {
                        node = ((NodeActionResult) result).getNode();
                    }
                    callBack.onExecuteDone(result, action);
                } else {
                    callBack.onExecuteCancel(action);
                }
            } else {
                action.execute();
            }
        }
    }
}
