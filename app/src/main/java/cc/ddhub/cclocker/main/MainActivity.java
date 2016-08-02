package cc.ddhub.cclocker.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cc.ddhub.cclocker.R;
import cc.ddhub.cclocker.app.AppLoader;
import cc.ddhub.cclocker.locker.view.AppListPresenter;
import cc.ddhub.cclocker.locker.view.AppListView;
import cc.ddhub.cclocker.util.ViewUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_locker);

        AppListView listView = ViewUtil.findViewById(getWindow().getDecorView(), R.id.appListView);
        AppListPresenter presenter = AppListPresenter.handle(listView);
        presenter.setAppInfoList(new AppLoader().getInstallApps(this));
    }
}
