import com.cabify.mobilechallenge.shared.cart.data.datasource.CartPersistenceDataSource
import com.cabify.mobilechallenge.shared.cart.domain.entity.CartEntity
import com.cabify.mobilechallenge.core.base.mapper.Mapper
import com.cabify.mobilechallenge.persistence.dao.CartDAO
import com.cabify.mobilechallenge.persistence.entity.CartItemData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

internal class CartPersistenceDataSourceImpl(
    private val cartDao: CartDAO<CartItemData>,
    private val cartItemsDataMapper: Mapper<List<CartItemData>, CartEntity>,
    private val cartItemEntityMapper: Mapper<CartEntity.Item, CartItemData>
) :
    CartPersistenceDataSource {

    override fun changes(): Observable<CartEntity> =
        cartDao.changes().map(cartItemsDataMapper::map)

    override fun upsertQuantity(item: CartEntity.Item): Completable =
        cartDao.upsertQuantity(cartItemEntityMapper.map(item))

    override fun deleteCart(): Completable =
        cartDao.delete()
}
