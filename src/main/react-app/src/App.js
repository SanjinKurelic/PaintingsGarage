import Header from './components/header/Header'
import ImageGallery from './components/image/ImageGallery'
import 'bootstrap/dist/css/bootstrap.min.css'
import {Container} from 'react-bootstrap'
import {useDispatch, useSelector} from 'react-redux'
import {useEffect} from 'react'
import {fillLastImages} from './redux/imageSlice'

function App() {
  const {images} = useSelector((state) => state.imageSlice)
  const dispatch = useDispatch()

  useEffect(() => {
    fetch('http://localhost:8080/api/search/image/latest')
      .then(response => {
        if (response.ok) {
          return response.json()
        }
        throw new Error('' + response.status)
      })
      .then(data => dispatch(fillLastImages(data)))
      .catch(status => console.log('Error while fetching latest images from server, status: ' + status))
  }, [dispatch])

  return (
    <Container>
      <Header/>
      <ImageGallery images={images}/>
    </Container>
  )
}

export default App
