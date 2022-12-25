angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080/app';


    $scope.loadProducts = function () {
        $http.get(contextPath + '/products')
            .then(function (response) {
                $scope.min = null;
                $scope.max = null;
                $scope.ProductsList = response.data
                $scope.ProductsList.sort(function (a, b) {
                    return parseFloat(a.id) - parseFloat(b.id);
                }); // сортировка по ID
                // console.log(ProductsList)
            });

    };

    $scope.deleteProduct = function (id) {
        console.log('Click deleteProduct', id);
        $http.get(contextPath + '/products/delete/' + id)
            .then(function (response) {
                $scope.loadProducts();
            });
    }

    //
    $scope.changePrice = function (id, delta) {
        console.log('Click changePrice!', id);
        $http({
            url: contextPath + '/products/change_price',
            method: 'GET',
            params: {
                id: id,
                delta: delta
            }
        }).then(function (response) {
            $scope.loadProducts();
        });
    }

    $scope.addProduct = function () {
        console.log('Click addProduct!', $scope.newProduct);
        $http.post(contextPath + '/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct.name = null;
                $scope.newProduct.price = null;
                $scope.newProduct.manufacturer = null;
                $scope.loadProducts();
            });
    }


    $scope.addFilter = function (minPrice, maxPrice) {
        if (minPrice === undefined || minPrice === null) minPrice = 0;
        if (maxPrice === undefined || maxPrice === null) maxPrice = Math.max.apply(null, $scope.ProductsList.map(o => o.price));

        console.log('Click addFilter', minPrice, maxPrice);

        $http.get(contextPath + '/products/find_price/' + minPrice + '&' + maxPrice)
            .then(function (response) {
                $scope.ProductsList = response.data
                $scope.ProductsList.sort(function (a, b) {
                    return parseFloat(a.id) - parseFloat(b.id);
                });
            });
    }


    $scope.loadProducts();

});
