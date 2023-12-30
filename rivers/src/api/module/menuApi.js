
'use strict';
import {post} from '@/utils';

export const menuApi = {
    fetchList(qry) {
        return post('/menu/list', {}, qry, {
            'Content-Type':'application/json;charset=utf-8'
        });
    },
    fetchPageable(qry) {
        return post('/menu/pageable', {}, qry, {
            'Content-Type':'application/json;charset=utf-8'
        });
    },
    add(data) {
        return post('/menu/add', {}, data, {
            'Content-Type':'application/json;charset=utf-8'
        });
    },
    update(data) {
        return post('/menu/update', {}, data, {
            'Content-Type':'application/json;charset=utf-8'
        });
    },
};