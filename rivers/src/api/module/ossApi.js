'use strict';
import {post} from '@/utils';

export const ossApi = {
    upload(data) {
        return post('/oss/upload', '', data, {
            'Content-Type': 'application/json;charset=utf-8'
        });
    },
    recover(data) {
        return post('/oss/recover', '', data, {
            'Content-Type': 'application/json;charset=utf-8'
        });
    },
    rollBackRecord(data) {
        return post('/oss/rollBackRecord', '', data, {
            'Content-Type': 'application/json;charset=utf-8'
        });
    }
};