'use strict';
import {post} from '@/utils';

export const roleApi = {
    fetchList(qry) {
        return post('/role/list', {}, qry, {
            'Content-Type':'application/json;charset=utf-8'
        });
    },
    fetchPageable(qry) {
        return post('/role/pageable', {}, qry, {
            'Content-Type':'application/json;charset=utf-8'
        });
    },
    add(data) {
        return post('/role/add', {}, data, {
            'Content-Type':'application/json;charset=utf-8'
        });
    },
    update(data) {
        return post('/role/update', {}, data, {
            'Content-Type':'application/json;charset=utf-8'
        });
    },
};