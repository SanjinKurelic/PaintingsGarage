import {Button, Container} from 'react-bootstrap'
import Header from './components/header/Header'
import ImageGallery from './components/image/ImageGallery'
import {useState} from 'react'
import Image from './components/image/Image'
import Footer from './components/footer/Footer'
import {BrowserRouter, Route, Switch} from 'react-router-dom'
import Login from './components/login/Login'
import User from './components/user/User'
import ProtectedRoute from './util/ProtectedRoute'
import Admin from './components/admin/Admin'
import {useSelector} from 'react-redux'
import {selectCurrentDialog} from './redux/slice/currentDialogSlice'
import {getDialog} from './components/dialog/AllDialogs'
import {useGetPhotoListQuery} from './redux/api/baseApi'

function App() {
  const latestImages = useGetPhotoListQuery()
  const [selectedImage, setSelectedImage] = useState(null)
  const [searchImageResults, setSearchImageResults] = useState(null)
  const [searchFired, setSearchFired] = useState(false)
  const currentDialog = useSelector(selectCurrentDialog)

  const images = () => {
    if (searchFired && searchImageResults.isSuccess) {
      return searchImageResults.data
    }
    if (!searchFired && latestImages.isSuccess) {
      return latestImages.data
    }
    return []
  }

  return (
    <BrowserRouter>
      <Header setSearchImageResults={setSearchImageResults} setSearchFired={setSearchFired}/>
      <Container style={{marginBottom: '58px'}}>
        <Switch>
          <Route path="/" exact render={() => (
            <>
              {currentDialog && getDialog(currentDialog.type, currentDialog.data)}
              {(images().length === 0 && searchFired) && <div className="m-3 fst-italic text-danger">
                Sorry, but nothing matched your search terms. Please try again with some different keywords or&nbsp;
                <Button variant="link" className="fst-italic p-0 align-baseline" onClick={() => setSearchFired(false)}>view
                  latest images.</Button>
              </div>}
              <ImageGallery images={images()} setSelectedImage={setSelectedImage}/>
              {selectedImage && <Image image={selectedImage} setSelectedImage={setSelectedImage}/>}
            </>
          )}/>
          <Route path="/login" component={Login}/>
          <ProtectedRoute path="/user" componentForRole={{user: User, admin: Admin}}/>
        </Switch>
      </Container>
      <Footer/>
    </BrowserRouter>
  )
}

export default App
