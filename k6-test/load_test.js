import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    vus: 10, // Số lượng người dùng ảo
    duration: '30s', // Thời gian kiểm thử
};

export default function () {
    // Sửa URL ở đây
    let res = http.get('http://localhost:8090/product-service/api/v1/product');
    
    // Kiểm tra phản hồi
    check(res, {
        'status is 200': (r) => r.status === 200,
        'response time is less than 200ms': (r) => r.timings.duration < 200,
    });
    
    // Nghỉ giữa các request
    sleep(1);
}
