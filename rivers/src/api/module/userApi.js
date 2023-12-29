'use strict';
import {post} from '@/utils';

export const userApi = {
    fetchPageable(qry) {
        return post('/user/list', {}, qry, {
            'Content-Type':'application/json;charset=utf-8'
        });
    },
    add(data) {
        return post('/user/add', {}, data, {
            'Content-Type':'application/json;charset=utf-8'
        });
    },
    update(data) {
        return post('/user/update', {}, data, {
            'Content-Type':'application/json;charset=utf-8'
        });
    },
};