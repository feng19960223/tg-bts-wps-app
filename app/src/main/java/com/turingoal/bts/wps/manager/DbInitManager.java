package com.turingoal.bts.wps.manager;

import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.turingoal.bts.wps.app.TgApplication;
import com.turingoal.bts.wps.bean.MaintenanceTaskDb;
import com.turingoal.bts.wps.bean.MaintenanceWorkItemDb;
import com.turingoal.bts.wps.bean.TrackDb;
import com.turingoal.bts.wps.bean.TrainSetDb;
import com.turingoal.bts.wps.bean.WorkConfigDb;
import com.turingoal.bts.wps.service.MaintenanceTaskService;
import com.turingoal.bts.wps.service.MaintenanceWorkItemService;
import com.turingoal.bts.wps.service.TrackService;
import com.turingoal.bts.wps.service.TrainSetService;
import com.turingoal.bts.wps.service.WorkConfigService;
import com.turingoal.bts.wps.util.TgRetrofitCallback;

import java.io.File;
import java.util.List;


/**
 * 数据库初始化
 */
public final class DbInitManager {
    private DbInitManager() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * 初始化数据库数据
     */
    public static void initData() {
        initWorkConfigDatas(); //  初始化[作业配置]数据
        initTrackDatas(); //  初始化[股道]数据
        initTrainSetDatas(); // 初始化[车号]数据
        initMaintenanceTaskDatas(); // 初始化[检修任务]数据
        initMaintenanceWorkItemDatas(); // 初始化[作业项目]数据
    }

    /**
     * 初始化[作业配置]数据
     */
    private static void initWorkConfigDatas() {
        TgApplication.getRetrofit().create(WorkConfigService.class)
                .list()
                .enqueue(new TgRetrofitCallback<List<WorkConfigDb>>(null, false, false) {
                    @Override
                    public void successHandler(List<WorkConfigDb> list) {
                        if (list != null && list.size() > 0) {
                            TgApplication.getBoxStore().boxFor(WorkConfigDb.class).removeAll();
                            for (WorkConfigDb workConfig : list) {
                                new ImageCacheAsyncTask(workConfig).execute();
                            }
                        }
                    }
                });
    }

    /**
     * 初始化[股道]数据
     */
    private static void initTrackDatas() {
        TgApplication.getRetrofit().create(TrackService.class)
                .list()
                .enqueue(new TgRetrofitCallback<List<TrackDb>>(null, false, false) {
                    @Override
                    public void successHandler(List<TrackDb> list) {
                        TgApplication.getBoxStore().boxFor(TrackDb.class).removeAll();
                        TgApplication.getBoxStore().boxFor(TrackDb.class).put(list);
                    }
                });
    }

    /**
     * 初始化[车号]数据
     */
    private static void initTrainSetDatas() {
        TgApplication.getRetrofit().create(TrainSetService.class)
                .list()
                .enqueue(new TgRetrofitCallback<List<TrainSetDb>>(null, false, false) {
                    @Override
                    public void successHandler(List<TrainSetDb> list) {
                        TgApplication.getBoxStore().boxFor(TrainSetDb.class).removeAll();
                        TgApplication.getBoxStore().boxFor(TrainSetDb.class).put(list);
                    }
                });
    }

    /**
     * 初始化[检修任务]数据
     */
    private static void initMaintenanceTaskDatas() {
        TgApplication.getRetrofit().create(MaintenanceTaskService.class)
                .list()
                .enqueue(new TgRetrofitCallback<List<MaintenanceTaskDb>>(null, false, false) {
                    @Override
                    public void successHandler(List<MaintenanceTaskDb> list) {
                        TgApplication.getBoxStore().boxFor(MaintenanceTaskDb.class).removeAll();
                        TgApplication.getBoxStore().boxFor(MaintenanceTaskDb.class).put(list);
                    }
                });
    }

    /**
     * 初始化[作业项目]数据
     */
    private static void initMaintenanceWorkItemDatas() {
        TgApplication.getRetrofit().create(MaintenanceWorkItemService.class)
                .list()
                .enqueue(new TgRetrofitCallback<List<MaintenanceWorkItemDb>>(null, false, false) {
                    @Override
                    public void successHandler(List<MaintenanceWorkItemDb> list) {
                        TgApplication.getBoxStore().boxFor(MaintenanceWorkItemDb.class).removeAll();
                        TgApplication.getBoxStore().boxFor(MaintenanceWorkItemDb.class).put(list);
                    }
                });
    }

    /**
     * 缓存[作业配置]的图片
     */
    private static class ImageCacheAsyncTask extends AsyncTask<Void, Void, File> {
        private WorkConfigDb workConfig;

        public ImageCacheAsyncTask(WorkConfigDb workConfig) {
            this.workConfig = workConfig;
        }

        @Override
        protected File doInBackground(Void... voids) {
            try {
                return Glide.with(TgApplication.getContext()).downloadOnly().load(workConfig.getStandardImgUrl()).submit().get();
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(final File result) {
            if (result != null) {
                workConfig.setStandardImgPathLocal(result.getPath()); // 此path就是对应文件的缓存路径
                TgApplication.getBoxStore().boxFor(WorkConfigDb.class).put(workConfig);
            }
        }
    }
}