function delFruit(fid){
    if(confirm('是否确认删除？')){
        window.location.href='fruit.do?fid='+fid+'&operate=del';
    }
}

function page(pageNo) {
    window.location.href = 'fruit.do?pageNo=' + pageNo;//（此处没有加operate参数）则服务器端获取的operate为空，则默认index方法
}