'use strict';
import {post} from '@/utils';

export const ocrApi = {
    fetchOcrText(data) {
        return post('/ocr/fetchOcrText', '', data, {
            'Content-Type': 'application/json;charset=utf-8'
        });
    },
};