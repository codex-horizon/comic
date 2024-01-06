'use strict';
import {post} from '@/utils';

export const translateApi = {
    fetchTranslateText(data) {
        return post('/translate/fetchTranslateText', '', data, {
            'Content-Type': 'application/json;charset=utf-8'
        });
    },
};