'use strict';
import {post} from '@/utils';

export const comicApi = {
    fetchPageable(params,qry) {
        return post('/comic/pageable', params, qry, {
            'Content-Type':'application/json;charset=utf-8'
        });
    },
    fetchList(qry) {
        return post('/comic/list', {}, qry, {
            'Content-Type':'application/json;charset=utf-8'
        });
    },
};