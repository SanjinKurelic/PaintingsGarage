import error404 from '../../assets/404.png'
import {Button} from 'react-bootstrap'
import {useHistory} from 'react-router-dom'

const NotFound = () => {
  const history = useHistory()

  return (
    <>
      <div className="text-center m-5"><img alt="" src={error404}/></div>
      <div className="m-3 fst-italic alert alert-warning">
        Requested page was not found, please go to home screen by clicking this link:&nbsp;
        <Button variant="link" className="fst-italic p-0 align-baseline" onClick={() => {
          history.push('/')
        }}>Home page</Button>
      </div>
    </>
  )
}

export default NotFound
