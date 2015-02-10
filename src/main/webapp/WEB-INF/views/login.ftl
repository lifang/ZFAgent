    <script src="<@spring.url "/resources/js/jquery-1.11.2.min.js"/>"></script>
    <script src="<@spring.url "/resources/js/jquery.form.min.js"/>"></script>
    login
<form action="<@spring.url "/login"/>" method="post">
    <input name="passport" type="text">
    <input name="password" type="password">
    <input name="captcha" type="text">
    <input type="submit" value="提交">
</form>
<img src="<@spring.url "/captcha"/>" />

<script>
    $(function(){
        $("form").ajaxForm({
            success : function(data){
                if(data.code==1){
                    window.location.href = "<@spring.url ""/>"+data.result;
                }
                if(data.code==-1){
                    alert(data.message);
                }
            }
        });
    });
</script>