<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">Поиск товаров</h1><hr>
    <form method="get" action="/">
        <div class="form-row">
            <div class="form-group col-md-2">
                <label for="searchBrand">Бренд</label>
                <select name="searchBrand" id="searchBrand" class="form-control">
                    <option value="">Бренд не выбран</option>
                    <option value="Polaroid">Polaroid</option>
                    <option value="Hugo Boss">Hugo Boss</option>
                    <option value="Ray-Ban">Ray-Ban</option>
                    <option value="Carrera">Carrera</option>
                </select>
            </div>
            <div class="form-group col-md-10">
                <label for="searchWord">Поиск</label>
                <input type="text" name="searchWord" <#if searchWord??>value="${searchWord}"</#if> class="form-control"
                       id="searchWord">
            </div>
            <button style="width: 100%" type="submit" class="btn btn-dark">Поиск</button>
        </div>
    </form>
    <br>
     <#if !user.email?? && !user.isAdmin()>
        <form action="/login" method="get" >
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-primary" style="width: 100%">Авторизируйтесь для покупок</button>
        </form>
    </#if>
    <hr>
    <div class="card-columns">
        <#list products as product>
            <a href="/product/${product.id}" class="product-link">
                <div class="card text-white bg-dark my-3 product">
                    <img src="/images/${product.previewImageId}"
                         class="card-img-top">
                    <div class="card-body">
                        <h5 class="card-title">${product.title}</h5>
                        <p class="card-text">${product.price} ₽</p>
                        ${product.brand}
                    </div>
                    <div class="card-footer">
                        <#if user.email?? && !user.isAdmin()>
                            <form action="/basket/add/${product.id}" method="post">
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                <button class="btn btn-outline-success" style=" width: 100%">Добавить в корзину</button>
                            </form>
                        </#if>
                    </div>
                </div>
            </a>
        <#else>
            <h1 style="text-align: center">Не найдено</h1>
        </#list>
    </div>
</@t.template>