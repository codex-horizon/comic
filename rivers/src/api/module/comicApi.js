'use strict';
import {post} from '@/utils';

export const comicApi = {
    fetchPageable(qry) {
        return post('/comic/list', {}, qry, {
            'Content-Type':'application/json;charset=utf-8'
        });
    },
};