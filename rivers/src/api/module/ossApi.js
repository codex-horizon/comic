'use strict';
import {post} from '@/utils';

export const ossApi = {
    upload(data) {
        return post('/oss/upload', '', data, {
            'Content-Type': 'application/json;charset=utf-8'
        });
    },
};