<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h2>Ваш заказ успешно обработан</h2>
    <hr>
    <div class="btn-group" role="group" aria-label="Basic outlined example">
        <form action="/" method="get">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-outline-success">Назад к покупкам</button>
        </form>
        <form action="/profile" method="get">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-outline-success">Перейти в профиль</button>
        </form>
    </div>
</@t.template>